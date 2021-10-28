import styled from 'styled-components';

const colorsBackground = ['#F5B0CB', '#745c97', '#39375b', '#201E1F', '#3F6C51'];

export const Card = styled.div`
  padding: 15px 10px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;

  .preview {
    width: 90px;
    height: 90px;
  }

  .content {
    margin-left: 10px;
    height: 80px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;

    .name {
      font-family: 'Lato';
      font-weight: 500;
      font-size: 14px;
      color: black;
    }

    .endereco {
      margin: 5px 0;
      font-family: 'Lato';
      font-weight: 300;
      font-size: 12px;
      color: #4e4e4e;
    }

    .buttons {
      width: 100%;
      margin-top: auto;

      .remover {
        margin-left: auto;
        background-color: white;
        height: 25px;
        padding: 0 10px;
        border-radius: 15px;
        border: 1px solid #ff6961;
        color: #ff6961;

        &:hover {
          cursor: pointer;
          background-color: #ff6961;
          color: white;
        }
      }
    }
  }
`;

export const NoImage = styled.div`
  width: 90px;
  height: 90px;
  background-color: ${colorsBackground[Math.floor(Math.random() * 4)]};
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;

  p {
    margin: auto 0;
    width: 90px;
    text-align: center;
    font-size: 32px;
    font-weight: 500;
    color: #c4bbb8;
  }
`;
