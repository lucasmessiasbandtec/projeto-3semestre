import styled from 'styled-components';

export const Container = styled.div`
  margin-top: 16px;
  height: 300px;
  width: 600px;
  overflow: scroll;
  overflow-x: hidden;
`;

export const ContainerEstabelecimento = styled.div`
  display: flex;
  margin-bottom: 16px;
  align-items: center;
  overflow-x: hidden;

  img {
    object-fit: cover;
    height: 100px;
    width: 100px;
    margin-right: 50px;
  }
`;

export const NoContent = styled.h2`
  font-size: 24px;
  color: black;
  font-weight: 600;
  margin-top: 24px;
`;
