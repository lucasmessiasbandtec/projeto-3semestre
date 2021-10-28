import React, { useContext } from 'react';
import Login from '../../components/Organisms/Login/Login';
import Layout from '../../components/Layout';
import DispatchContext from '../../DispatchContext';
import Footer from '../../components/Organisms/Footer/Footer';

const Autenticacao = () => {
  const appDispatch = useContext(DispatchContext);
  appDispatch({ type: 'is-institutional' });

  return (
    <Layout>
      <Login />
      <Footer />
    </Layout>
  );
};

export default Autenticacao;
