import styled from 'styled-components';

let mainWidth = '1100px';
let sideWidth = '140px';
let contentWidth = '960px';

export const Container = styled.div`
  width: ${mainWidth};
  border: 1px solid #dcdcdc;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
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
      object-fit: cover;
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
        height: 20px;
        margin-bottom: 5px;
      }

      p {
        text-align: center;
        font-size: 14px;
      }
    }
  }
`;

export const Content = styled.div`
  margin: 20px 0;
  width: ${contentWidth};

  .name {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;

    p {
      font-size: 18px;
      color: black;
      margin: 10px 0;
    }

    .date {
      color: #5f5f5f;
      font-size: 12px;
      margin-left: auto;
      margin-right: 20px;
    }
  }

  .comment {
    margin-right: 10px;
    margin-top: 10px;
    margin-bottom: 20px;
    font-size: 16px;
    font-family: 'Lato';
    font-weight: 300;
    color: #5f5f5f;
  }

  .photo {
    margin: 10px 0;
    margin-right: 40px;
    width: 940px;
    height: 150px;
  }
`;
