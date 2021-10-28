import React from 'react';
import { Navbar, Container, LogoWrapper, MenuItems } from './ExternNavbar.style';
import { ReactComponent as Logo } from '../../../assets/logo-blue.svg';
import { Link } from 'react-router-dom';
import StateContext from '../../../StateContext';
import { useContext } from 'react';

const ExternNavbar = ({ children }) => {
  const appState = useContext(StateContext);

  return (
    <Navbar>
      <Container>
        <LogoWrapper>
          <Link to="/home">
            <Logo />
          </Link>
          {!appState.isInstitutionalPage ? (
            <MenuItems>
              <Link to="/">
                <li>Home</li>
              </Link>
              <Link to="/home">
                <li>Posts</li>
              </Link>
              <Link to="/dashboard">
                <li>Criar Viagem</li>
              </Link>
              <Link to="/itinerario">
                <li>Itinerário</li>
              </Link>
              <Link to="/ultimasViagens">
                <li>Últimas viagens</li>
              </Link>
            </MenuItems>
          ) : (
            <></>
          )}
        </LogoWrapper>
        {children}
      </Container>
    </Navbar>
  );
};

export default ExternNavbar;
