import React, { useContext } from 'react';
import { Redirect } from 'react-router-dom';
import Layout from '../../components/Layout';
import ContainerMaps from '../../components/Organisms/ContainerMaps/ContainerMaps';

import StateContext from '../../StateContext';

const Dashboard = () => {
  const appState = useContext(StateContext);

  if (!appState.logged) {
    return <Redirect to="/auth" />;
  }

  return (
    <Layout>
      <div className="wrapper">
        <ContainerMaps />
      </div>
    </Layout>
  );
};

export default Dashboard;
