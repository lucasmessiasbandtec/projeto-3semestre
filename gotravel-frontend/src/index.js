import Institucional from './pages/institucional/institucional';
import Autenticacao from './pages/autenticacao/autenticacao.js';
import Publicacoes from './pages/publicacoes/publicacoes.js';
import Dashboard from './pages/dashboard/dashboard';
import Viagens from './pages/viagens/viagens';
import StateContext from './StateContext';
import DispatchContext from './DispatchContext';
import Itinerarios from './pages/itinerario/itinerario.js';
import React, { useEffect } from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { useImmerReducer } from 'use-immer';
import Axios from 'axios';
import Navbar from './components/Organisms/Navbar/Navbar';
//Axios.defaults.baseURL = 'http://localhost:8080';
 Axios.defaults.baseURL = 'https://go-travel-ads1.herokuapp.com';

function Index() {
  const initialState = {
    logged: Boolean(localStorage.getItem('gotravelUserEmail')),
    user: {
      email: localStorage.getItem('gotravelUserEmail'),
      id: localStorage.getItem('gotravelUserId'),
      name: localStorage.getItem('gotravelUserName'),
      jwtkey: localStorage.getItem('JWTTOKEN'),
    },
    isInstitutionalPage: false,
    formType: true,
    formPost: false,
    selectTrip: false,
    refreshPosts: false,
  };

  function ourReducer(draft, action) {
    switch (action.type) {
      case 'login':
        draft.logged = true;
        draft.user = action.data;
        return;
      case 'logout':
        draft.logged = false;
        return;
      case 'form-login':
        draft.formType = true;
        return;
      case 'form-register':
        draft.formType = false;
        return;
      case 'is-institutional':
        draft.isInstitutionalPage = true;
        return;
      case 'is-not-institutional':
        draft.isInstitutionalPage = false;
        return;
      case 'show-form-post':
        draft.formPost = !draft.formPost;
        return;
      case 'hide-form-post':
        draft.formPost = false;
        return;
      case 'show-select-trip':
        draft.formPost = !draft.selectTrip;
        return;
      case 'refresh-posts':
        draft.refreshPosts = !draft.refreshPosts;
        return;
    }
  }

  const [state, dispatch] = useImmerReducer(ourReducer, initialState);

  useEffect(() => {
    if (state.logged) {
      localStorage.setItem('gotravelUserEmail', state.user.email);
      localStorage.setItem('gotravelUserId', state.user.id);
      localStorage.setItem('gotravelUserName', state.user.name);
      localStorage.setItem('JWTTOKEN', state.user.jwtkey);
    } else {
      localStorage.removeItem('gotravelUserEmail');
      localStorage.removeItem('gotravelUserId');
      localStorage.removeItem('gotravelUserName');
      localStorage.removeItem('JWTTOKEN');
    }
  }, [state.logged]);

  return (
    <StateContext.Provider value={state}>
      <DispatchContext.Provider value={dispatch}>
        <BrowserRouter>
          <Navbar />

          <Switch>
            <Route path="/" exact>
              <Institucional />
            </Route>
            <Route path="/home" exact>
              <Publicacoes />
            </Route>
            <Route path="/auth">
              <Autenticacao />
            </Route>
            <Route path="/dashboard">
              <Dashboard />
            </Route>
            <Route path="/ultimasViagens">
              <Viagens />
            </Route>
            <Route path="/itinerario">
              <Itinerarios />
            </Route>
          </Switch>
        </BrowserRouter>
      </DispatchContext.Provider>
    </StateContext.Provider>
  );
}

ReactDOM.render(<Index />, document.getElementById('root'));
