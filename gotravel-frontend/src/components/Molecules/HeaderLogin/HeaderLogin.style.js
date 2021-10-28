import styled, { keyframes } from 'styled-components';

const menuIn = keyframes`
  0% {
    height: 0px;
  }
  
  100% {
    height: 110px;
  }
`;

const menuOut = keyframes`
  0% {
    height: 110px;
  }
  
  100% {
    height: 0px;
  }
`;

const LogoffWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;

  .profile {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;

    &:hover {
      cursor: pointer;
    }

    .profilePic {
      height: 40px;
      width: 40px;
      border-radius: 35px;
      box-shadow: 0px 0px 1px 1px #aaaaaa;
    }

    .rectangle {
      height: 8px;
      margin-left: 10px;
    }
  }

  p {
    margin-right: 25px;
    font-size: 14px;
    font-weight: 300px;
  }

  .options {
    /* height: 110px; */
    position: absolute;
    margin-top: 160px;
    margin-left: 120px;
    border: 1px solid #bebebe;
    background-color: white;
    z-index: 999;

    li {
      background-color: white;
      border: 1px solid #bebebe;
      padding: 20px 20px;
      font-size: 14px;
      font-weight: 500;
      color: #5f5f5f;
      z-index: 999;

      &:hover {
        cursor: pointer;
        background-color: #f3f3f3;
        transition: background-color 0.25s;
      }
    }
  }

  .visible {
    display: block;
    /* animation: ${menuIn} 0.25s ease-in 1; */
  }

  .notvisible {
    display: none;
    /* animation: ${menuOut} 0.25s ease-in 1; */
  }
`;

export default LogoffWrapper;
