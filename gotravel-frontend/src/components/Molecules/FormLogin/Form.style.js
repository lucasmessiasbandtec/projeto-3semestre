import styled, { keyframes } from 'styled-components';

const rotate = keyframes`
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
`;

const shrink = keyframes`
  0% {
    width: 200px;
  }

  /* 8% {
    width: 210px;
  }

  15% {
    width: 210px;
  } */

  100% {
    width: 60px;
  }
`;

export const FirstContainer = styled.div`
  display: flex;
  width: 100%;
  height: 100vh;
  flex-direction: row;
  justify-content: center;
  align-content: center;
  background-color: #d9ffe8;

  h1 {
    font-weight: 600;
    font-size: 35px;
  }

  spam {
    color: #2d73dd;
  }
`;

export const ArtContainer = styled.div`
  width: 100%;
  height: 100%;

  svg {
    width: 100%;
    height: 100%;
  }
`;

export const FormContainer = styled.div`
  display: flex;
  width: 100%;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const FormStyle = styled.form`
  color: black;

  small {
    text-align: center;
    font-size: 14px;
    color: red;
  }

  input {
    background: #ffffff;
    border: 1px solid #000000;
    box-sizing: border-box;
    box-shadow: 2px 5px 15px 0px #808080;
    border-radius: 30px;
    width: 350px;
    height: 55px;
    outline: 0;
    padding-left: 20px;
  }

  div {
    display: flex;
    flex-direction: column;
    margin: 15px;
  }

  button {
    width: 200px;
    height: 50px;
    margin-top: 20px;
    border: 0;
    box-shadow: 0px 5px 5px 0px #7c7c7c;
    border-radius: 5px;
    background-color: #f0f0f0;
    color: black;
    font-size: 15px;
  }

  .login {
    background-color: #2d73dd;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .loading {
    width: 60px;
    animation: ${shrink} 1s ease-out 1;

    img {
      height: 30px;
      animation: ${rotate} 1s linear infinite;
    }
  }
`;

export const ContainerButtons = styled.div`
  display: flex;
  align-items: center;
`;
