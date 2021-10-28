import React, { useContext } from 'react';
import DispatchContext from '../../DispatchContext';
import StateContext from '../../StateContext';
import Layout from '../../components/Layout';
import { Redirect } from 'react-router-dom';
import Itinerario from '../../components/Organisms/Itinerario';

const Itinerarios = (props) => {
  const appDispatch = useContext(DispatchContext);
  appDispatch({ type: 'is-not-institutional' });

  console.log('ITINERARIO', props);

  const appState = useContext(StateContext);

  if (!appState.logged) {
    return <Redirect to="/auth" />;
  }

  return (
    <Layout>
      <Itinerario />
    </Layout>
  );
};

export default Itinerarios;
