import Axios from 'axios';
import React, { useContext, useEffect, useState } from 'react';

import { Container } from './style';

import StateContext from '../../../StateContext';

function SelectTrip() {
  const [trips, setTrips] = useState();

  const appState = useContext(StateContext);

  useEffect(() => {
    const userId = appState.user.id;

    Axios.get(`/trip/${userId}`, { headers: { authorization: appState.user.jwtkey } })
      .then((response) => {
        setTrips(response.data.content);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <Container>
      <ul>{/* {trips.map((trip) => {
          <li>CCCC</li>;
        })} */}</ul>
    </Container>
  );
}

export default SelectTrip;
