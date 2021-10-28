import React, { useContext } from 'react';
import Posts from '../../components/Organisms/Posts/index';
import Layout from '../../components/Layout';
import DispatchContext from '../../DispatchContext';
import StateContext from '../../StateContext';
import AddPost from '../../components/Molecules/AddPost';
import FormPost from '../../components/Molecules/FormPost/FormPost';
import SelectTrip from '../../components/Molecules/SelectTrip';
import { Redirect } from 'react-router-dom';

const Publicacoes = () => {
  const appDispatch = useContext(DispatchContext);
  appDispatch({ type: 'is-not-institutional' });

  const appState = useContext(StateContext);

  if (!appState.logged) {
    return <Redirect to="/auth" />;
  }

  return (
    <Layout>
      {appState.formPost ? <FormPost /> : <></>}
      {!appState.selectTrip ? <SelectTrip /> : <></>}

      <AddPost text="Adicionar Post"/>
      <Posts />
    </Layout>
  );
};

export default Publicacoes;
