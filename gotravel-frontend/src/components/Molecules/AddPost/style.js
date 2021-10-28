import styled from 'styled-components';

export const Container = styled.div`
  width: 1100px;
  height: 60px;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;

  div {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    margin: 20px 10px;

    &:hover {
      cursor: pointer;

      img {
        width: 29px;
        height: 29px;
        transform: translateY(-2px);
        transform: translateX(-2px);
      }
    }

    img {
      position: absolute;
      width: 25px;
      height: 25px;
      margin-left: 10px;
      transition: 0.2s;
    }

    p {
      font-size: 14px;
      color: black;
      text-align: center;
      margin-left: 45px;
    }
  }
`;
