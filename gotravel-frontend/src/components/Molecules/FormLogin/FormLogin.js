import React, { useContext, useState } from 'react';
import Axios from 'axios';
import Label from '../../Atoms/Label/label';
import { FirstContainer, ArtContainer, FormContainer, FormStyle, ContainerButtons } from './Form.style';
import { ReactComponent as Img } from '../../../assets/mulher-carro.svg';
import DispatchContext from '../../../DispatchContext';
import StateContext from '../../../StateContext';

import spinnersvg from '../../../assets/spinner-solid.svg';
import { Redirect } from 'react-router-dom';

const FormLogin = (props) => {
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [spinner, setSpinner] = useState(false);
  const [error, setError] = useState('');

  const appState = useContext(StateContext);
  const appDispatch = useContext(DispatchContext);

  if (appState.logged) {
    return <Redirect to="/home" />;
  }

  function handleSubmit(e) {
    e.preventDefault();

    if (!email || !password) {
      setError('Preencha todos os campos');
      return;
    }

    setSpinner(true);
    Axios.post('/login', { email, password })
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          const salvar = {
            email: response.data.email,
            id: response.data.id,
            name: response.data.name,
            jwtkey: response.headers.authorization,
          };

          setTimeout(() => {
            setError(false);
            setSpinner(false);
            appDispatch({ type: 'login', data: salvar });
          }, 1000);
        } else if (response.status === 404) {
          setError('Usuário ou senha incorretos');
          setSpinner(false);
        }
      })
      .catch((fail) => {
        console.error('EPIC FAIL: ', fail);
        setError('Usuário ou senha incorretos');
        setSpinner(false);
      });
  }

  return (
    <FirstContainer>
      <ArtContainer>
        <Img />
      </ArtContainer>

      <FormContainer>
        <h1>
          Dê um <spam>GO!</spam> e faça o login!
        </h1>

        <FormStyle autoComplete="off" onSubmit={handleSubmit}>
          <div>
            <div>
              <Label htmlFor="email" text={'E-mail'} />
              <input onChange={(e) => setEmail(e.target.value)} type="text" name="email" id="email" placeholder="email@provedor.com.br" />
            </div>

            {error ? <small>{error}</small> : <></>}

            <div>
              <Label htmlFor="password" text="Senha" />
              <input onChange={(e) => setPassword(e.target.value)} type="password" name="password" id="password" placeholder="****" />
            </div>

            <ContainerButtons>
              {!spinner ? (
                <button className="login" type="submit">
                  Logar
                </button>
              ) : (
                <button className="login loading" type="submit">
                  <img src={spinnersvg} alt="" />
                </button>
              )}

              <button onClick={() => appDispatch({ type: 'form-register' })}>Cadastrar uma conta</button>
            </ContainerButtons>
          </div>
        </FormStyle>
      </FormContainer>
    </FirstContainer>
  );
};

export default FormLogin;
