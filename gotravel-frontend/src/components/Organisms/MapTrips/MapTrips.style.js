import styled from 'styled-components';

export const TitleMapsWrapper = styled.div`
  height: 42px;
  display: flex;
  justify-content: center;
  border: 1px solid #cacaca;
  box-sizing: border-box;
  box-shadow: 0px 1px 4px rgba(0, 0, 0, 0.25);
  margin-top: 24px;
  align-items: center;

  svg {
    margin-right: 20px;
    margin-left: 20px;
  }
`;

export const TitleMaps = styled.h2`
  font-size: 16px;
  font-weight: 500;
`;

export const MapsWrapper = styled.div`
  height: 300px;
  border: 1px solid #cacaca;
  position: relative;

  .ver-itinerario {
    position: absolute;
    background: #00b12c;
    font-size: 16px;
    font-weight: 500;
    padding: 10px 25px;
    border: 0px;
    border-radius: 10px;
    bottom: 0;
    margin-left: 45px;
    margin-bottom: 15px;
    z-index: 999;

    &:hover {
      cursor: pointer;
      background: #0a922c;
      transition: 0.25s;
    }
  }
`;
export const ContainerGif = styled.div`
  text-align: center;
  img {
    height: 300px;
  }
`;
