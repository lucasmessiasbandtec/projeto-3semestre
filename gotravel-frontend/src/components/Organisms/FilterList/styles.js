import styled from 'styled-components';

export const Container = styled.div`
  background: white;
  width: 100%;
  height: 100%;
  overflow-y: auto;

  .titulo {
    margin: 20px;
    font-family: 'Montserrat';
    font-weight: 500;
    font-size: 20px;
    color: #1a73e8;
    text-align: center;
  }
`;

export const NothingHere = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: 'Lato';

  h1 {
    margin: 10px 0px;
    font-weight: bold;
    font-size: 24px;
    color: black;
  }

  h3 {
    margin-bottom: 20px;
    color: #808080;
  }

  img {
    width: 100px;
  }
`;
