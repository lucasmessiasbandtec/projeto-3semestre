import styled from 'styled-components';

export const ButtonWrapper = styled.button`
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
  &:hover {
    background-color: #216cff;
    color: #ffff;
  }
`;
