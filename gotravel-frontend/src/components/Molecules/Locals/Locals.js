import React, { useState, useEffect, useContext } from 'react';
import { Container, ContainerEstabelecimento, NoContent } from './Locals.style';
import Axios from 'axios';
import StateContext from '../../../StateContext';
import TitlePage from '../../Atoms/TitlePage';
import Error from '../../Organisms/Error';

const Locals = () => {
  const appState = useContext(StateContext);
  const [local, setLocal] = useState([]);
  const [trip, setTrip] = useState([
    {
      id: 'aa5fd690b3db7bb55eb06a5f6e',
      latMatch: '-3.222',
      lngMatch: '-34.533',
      latDestiny: '-3.115',
      lngDestiny: '-32.523',
      destiny: 'Avenida Paulista ; Rua Horácio de Carvalho',
      idUser: '5fc556949197b03c48816a9f',
    },
  ]);

  useEffect(() => {
    Axios.get(`trip/${appState.user.id}`, { headers: { authorization: appState.user.jwtkey } })
      .then((e) => {
        setTrip(e.data.content);
      })
      .catch((e) => {
        console.error(e);
      });
  }, []);

  useEffect(() => {
    Axios.get(`filter/${trip[0].id}`, { headers: { authorization: appState.user.jwtkey } })
      .then((e) => {
        setLocal(e.data);
      })
      .catch((e) => {
        console.error(e);
      });
  }, [trip]);

  const renderLocals = () => (
    <>
      <br />
      <TitlePage text="OS LOCAIS POR ONDE VOCÊ VAI PARAR" />
      <Container>
        {local.map((item) => (
          <ContainerEstabelecimento>
            {item.url ? <img src={item.url} /> : <img src={'https://st4.depositphotos.com/17828278/24401/v/600/depositphotos_244011872-stock-illustration-image-vector-symbol-missing-available.jpg'} />}
            <h1>{item.localName}</h1>
          </ContainerEstabelecimento>
        ))}
      </Container>
    </>
  );

  return local.length > 0 ? renderLocals() : <NoContent>Essa viagem não possui nenhum filtro</NoContent>;
};

export default Locals;
