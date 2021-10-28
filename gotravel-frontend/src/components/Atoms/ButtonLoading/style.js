import styled, { keyframes } from 'styled-components';

export const ButtonStyle = styled.button`
  height: 40px;
  width: 140px;
  text-align: center;
  font-size: 18px;
  font-weight: 500;
  background-color: transparent;
  cursor: pointer;
  border-radius: 5px;
  border-color: #2d73dd;
  color: ${(props) => props.color};
  animation: ${loading} 1s linear infinite;
`;

const loading = keyframes`
  0% {
    background: transparent;
  }

  50% {
    background: #2ddd7c;
  }

  100% {
    background: transparent;
  }
`;
