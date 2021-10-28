import styled, { keyframes } from 'styled-components';
import logoimg from '../../../assets/logo-blue.svg';

const rotate = keyframes`
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
`;

export const FormFiltros = styled.div`
  max-width: 1100px;
  width: 100%;
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  margin-top: 50px;
  padding: 20px;
  border-left: 1px solid #c3c3c3;
  border-right: 1px solid #c3c3c3;

  .title {
    width: 100%;
    margin-top: -40px;

    h2 {
      font-size: 28px;
      font-weight: 200;
      color: #818181;
    }
  }
`;

export const MapWrapper = styled.div`
  width: 100vw;
  height: 100vh;
  margin-top: -50px;

  .rotate-spinner {
  }

  .divider {
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;

    .formWrapper {
      height: calc(100vh - 70px);
      margin-top: 70px;
      width: 200px;
      left: 0;
      top: 0;
      position: fixed;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      z-index: 999;

      .form {
        height: 100%;
        width: fit-content;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
        padding-top: 10px;
        /* box-shadow: 5px 2px 15px 1px#525252; */
        background: #1a73e8;
        z-index: 99;

        h2 {
          margin: 10px 0;
          text-align: center;
          font-family: 'Lato';
          font-size: 16px;
          font-weight: 300;
          color: white;
        }

        .googleinput {
          display: flex;
          flex-direction: row;
          justify-content: center;
          align-items: center;
          margin: 0 20px;

          img {
            width: 15px;
            margin-right: 20px;
          }
        }

        .autocomplete {
          width: 240px;
          /* background-color: #22529b; */
          background-color: transparent;
          font-family: 'Lato';
          color: white;
          height: 18px;
          border: 0px;
          border-bottom: 1px solid #dadada;
          margin: 15px 0;
          font-size: 14px;
          text-align: left;
        }

        .openFilters {
          display: flex;
          flex-direction: row;
          justify-content: flex-start;
          align-items: flex-start;
          padding: 20px 20px;

          &:hover {
            cursor: pointer;
          }

          img {
            height: 20px;
          }

          p {
            margin-left: 10px;
            font-family: 'Lato';
            font-weight: 300;
            color: white;
          }
        }

        .filtersWrapper {
          width: 100%;
          margin-top: 10px;
          background-color: #0d376d;
        }

        .inputs {
          max-width: 100%;
          box-sizing: border-box;
          display: flex;
          flex-direction: column;
          align-items: flex-start;
          justify-content: flex-start;
          color: white;
          /* background-color: white; */
          padding-bottom: 10px;

          .filter {
            display: flex;
            flex-direction: row;
            margin: 8px 5px;

            .radio {
              font-size: 14px;
              color: white;
              display: grid;
              grid-template-columns: min-content auto;
              grid-gap: 0.5em;

              .radio__input {
                display: flex;

                input {
                  opacity: 0;
                  width: 0;
                  height: 0;
                }

                input + .radio__control::before {
                  content: '';
                  width: 0.5em;
                  height: 0.5em;
                  box-shadow: inset 0.5em 0.5em currentColor;
                  border-radius: 50%;
                  transition: 180ms transform ease-in-out;
                  transform: scale(0);
                }

                input:checked + .radio__control::before {
                  transform: scale(1);
                }
              }

              .radio__control {
                display: grid;
                place-items: center;
                width: 1em;
                height: 1em;
                border-radius: 50%;
                border: 0.1em solid white;
                transform: translateY(-0.05em);
              }
            }

            img {
              width: 15px;
              height: 15px;
              margin-right: 5px;
            }

            label {
              font-size: 16px;
            }
          }
        }
      }
    }

    .create-travel {
      position: absolute;
      top: 0;
      right: 0;
      margin-top: 200px;
      margin-right: 20px;
      z-index: 801;

      .buttons {
        .confirmar {
          line-height: 20px;
          padding: 15px 20px;
          border: 0px;
          border-radius: 10px;
          color: white;
          box-shadow: 5px 5px 2spx 1px #383838;
          font-family: 'Lato';
          font-size: 18px;
          display: flex;
          flex-direction: row;
          align-items: center;
          justify-content: center;

          img {
            height: 20px;
            margin-right: 5px;
          }
        }

        .habilitado {
          transition: 0.25s;
          background: linear-gradient(58deg, #6ee6b7, #3b82f6, #3b82f6);
          background-size: 600% 600%;

          -webkit-animation: AnimationName 30s ease infinite;
          -moz-animation: AnimationName 30s ease infinite;
          animation: AnimationName 30s ease infinite;

          @-webkit-keyframes AnimationName {
            0% {
              background-position: 0% 58%;
            }
            50% {
              background-position: 100% 43%;
            }
            100% {
              background-position: 0% 58%;
            }
          }
          @-moz-keyframes AnimationName {
            0% {
              background-position: 0% 58%;
            }
            50% {
              background-position: 100% 43%;
            }
            100% {
              background-position: 0% 58%;
            }
          }
          @keyframes AnimationName {
            0% {
              background-position: 0% 58%;
            }
            50% {
              background-position: 100% 43%;
            }
            100% {
              background-position: 0% 58%;
            }
          }

          &:hover {
            cursor: pointer;
            transform: scale(1.05);
          }
        }

        .nao-habilitado {
          background-color: #969696;
          color: white;
        }
      }
    }

    .map {
      height: 100%;
      width: 100%;
    }
  }
`;
