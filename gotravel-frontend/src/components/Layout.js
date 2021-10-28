import React from 'react';
import styled, { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

export const GlobalStyle = createGlobalStyle`
${reset}

@import url('https://fonts.googleapis.com/css?family=Montserrat:300,300i,400,400i,500,500i,600,700,700i&display=swap');

  html{
    scroll-behavior: smooth;
    font-size: 16;
    overflow-x: hidden;
  }
  
  body{
    font-family: 'Montserrat', sans-serif;
  }
  
  button{
    font-family: 'Montserrat', sans-serif;
    color: white;
    background-color: #334A52;
  }

  a {
    text-decoration: none;
  }

`;

const Main = styled.main`
  min-height: 100vh;
`;

const Layout = ({ children }) => {
  return (
    <Main>
      <GlobalStyle />
      {children}
    </Main>
  );
};

export default Layout;
