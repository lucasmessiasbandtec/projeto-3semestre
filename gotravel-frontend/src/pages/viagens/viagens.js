import React, { useContext } from 'react';
import { Redirect } from 'react-router-dom';
import Layout from '../../components/Layout';
import ContainerViagens from '../../components/Organisms/ContainerViagens';
import StateContext from '../../StateContext';

const UltimasViagens = () => {
  const appState = useContext(StateContext);

  if (!appState.logged) {
    return <Redirect to="/auth" />;
  }

  return (
    <Layout>
      <ContainerViagens />
    </Layout>
  );
};

export default UltimasViagens;
