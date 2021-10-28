import styled from 'styled-components';

export const ViagensWrapper = styled.div`
  max-width: 1000px;
  margin: 0 auto;

  .upload-wrapper {
    background-color: rgba(0, 0, 0, 0.4);
    position: absolute;
    top: 0;
    left: 0;
    margin: top 90px;
    z-index: 999;
    width: 100vw;
    height: 100vh;
  }

  .upload-div {
    max-width: 1100px;
    margin: 0 auto;
    margin-top: 40vh;
    width: fit-content;
    background-color: white;
    padding: 0 15px;
    box-shadow: 2px 2px 5px 2px #bebebe;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    h2 {
      font-family: 'Lato';
      font-size: 16px;
      padding: 10px 10px;
      border-bottom: 1px solid #bebebe;
      margin: 15px 0;
    }

    div {
      display: flex;
      flex-direction: row;
      justify-content: center;
      align-items: center;

      input {
        margin: 10px auto;
        font-family: 'Lato';
        font-size: 14px;
      }
    }

    .buttons {
      padding-top: 15px;
      margin: 10px 0;

      .enviar {
        background: linear-gradient(270deg, #05a86a, #05a86a);
        border: 0px;
        margin: 0 10px;
        padding: 10px 20px;
      }

      .cancelar {
        background: linear-gradient(270deg, #e2e2e2, #c2c2c2);
        color: black;
        border: 0px;
        margin: 0 10px;
        padding: 10px 20px;
      }
    }
  }
`;

export const BotoesWrapper = styled.div`
  display: flex;
  margin: 0 0 15px 0;
  max-width: 400px;
`;
