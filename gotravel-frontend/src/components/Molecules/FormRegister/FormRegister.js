import React, { useContext, useState } from 'react';
import Axios from 'axios';
import Label from '../../Atoms/Label/label';
import { FirstContainer, ArtContainer, FormContainer, FormStyle, ContainerButtons } from './Form.style';
import { ReactComponent as Img } from '../../../assets/mulher-carro.svg';
import DispatchContext from '../../../DispatchContext';

const FormRegister = (props) => {
  const [email, setEmail] = useState();
  const [name, setName] = useState();
  const [password, setPassword] = useState();

  const appDispatch = useContext(DispatchContext);

  function handleSubmit(e) {
    e.preventDefault();

    try {
      Axios.post('/user/register', { email, name, password })
        .then((response) => {
          if (response.status == 201) {
            console.log('Registrado com sucesso!');
            appDispatch({ type: 'form-login' });
          } else if (response.status == 422) {
            console.log('Já existe um usuário com esse email');
          }
        })
        .catch((error) => {
          console.log(error);
        });
    } catch (e) {
      console.log(e);
    }
  }

  return (
    <FirstContainer>
      <ArtContainer>
        <Img />
      </ArtContainer>

      <FormContainer>
        <h1>
          Dê um <spam>GO!</spam> e faça o cadastro!
        </h1>

        <FormStyle onSubmit={handleSubmit}>
          <div>
            <div>
              <Label htmlFor="name" text={'Nome'} />
              <input onChange={(e) => setName(e.target.value)} type="text" name="name" id="name" placeholder="Fulano de tal" />
            </div>

            <div>
              <Label htmlFor="email" text={'E-mail'} />
              <input onChange={(e) => setEmail(e.target.value)} type="text" name="email" id="email" placeholder="email@provedor.com.br" />
            </div>

            <div>
              <Label htmlFor="password" text="Senha" />
              <input onChange={(e) => setPassword(e.target.value)} type="password" name="password" id="password" placeholder="****" />
            </div>

            <ContainerButtons>
              <button type="submit">Cadastrar</button>

              <button onClick={() => appDispatch({ type: 'form-login' })}>Já tenho uma conta</button>
            </ContainerButtons>
          </div>
        </FormStyle>
      </FormContainer>
    </FirstContainer>
  );
};

export default FormRegister;
