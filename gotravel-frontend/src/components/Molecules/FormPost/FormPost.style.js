import styled, { keyframes } from 'styled-components';

let mainWidth = '1000px';
let sideWidth = '140px';
let contentWidth = '960px';

export const showUp = keyframes`
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
`;

export const Container = styled.div`
  position: absolute;
  margin-top: 70px;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  background-color: rgba(11, 11, 11, 0.7);
  z-index: 101;
  display: flex;
  flex-direction: row;
  justify-content: center;
  animation: ${showUp} 0.25s ease-in 1;

  .wrapper {
    position: absolute;
    width: ${mainWidth};
    border: 1px solid #dcdcdc;
    background-color: white;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 70px;
  }
`;

export const SideInfo = styled.div`
  margin: 20px 0;
  width: ${sideWidth};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .profile-pic {
    img {
      margin: 15px 0;
      width: 60px;
      height: 60px;
      border-radius: 80px;
    }
  }

  .actions {
    margin-top: auto;

    div {
      margin: 20px 0;
      display: flex;
      flex-direction: column;

      img {
        height: 25px;
      }

      p {
        text-align: center;
        font-size: 14px;
        color: #b9b9b9;
      }
    }
  }
`;

export const Content = styled.div`
  margin: 20px 20px;
  margin-left: 0;
  width: ${contentWidth};

  .name {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    p {
      font-weight: 500;
      font-size: 18px;
      color: black;
    }

    button {
      background-color: white;
      font-size: 20px;
      font-weight: 500;
      color: black;
      border: 0px;

      &:hover {
        cursor: pointer;
        color: red;
      }
    }
  }

  .comment {
    margin: 10px 0;
    margin-right: 20px;

    textarea {
      padding: 10px;
      height: 160px;
      width: 100%;
      background-color: #ececec;
      border: 0px;
      border-radius: 15px;
      font-family: 'Montserrat';
      font-size: 14px;
      resize: none;
    }
  }

  .buttons {
    margin-top: 20px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    font-size: 18px;

    .adicionar {
      background-color: #2ebca9;
      border-radius: 10px;
      border: 0px;
      padding: 10px 20px;

      &:hover {
        cursor: pointer;
        background-color: #27998a;
        transition: background-color 0.25s;
      }
    }

    .publicar {
      background-color: #70a9fe;
      border-radius: 10px;
      border: 0px;
      padding: 10px 20px;
    }
  }

  .yourTrips {
    position: absolute;
    width: fit-content;
    background-color: white;
    box-shadow: 1px 1px 4px 1px #c3c3c3;

    li {
      padding: 15px 10px;
      font-size: 14px;
      font-weight: 400;

      &:hover {
        cursor: pointer;
        background-color: #f2f2f2;
        transition: background-color 0.25s;
      }
    }

    .selected {
      background-color: #c2c2c2;
      transition: background-color 0.25s;
    }
  }
`;
