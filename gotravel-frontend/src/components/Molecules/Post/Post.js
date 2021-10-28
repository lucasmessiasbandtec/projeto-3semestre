import React, { useContext, useEffect, useState } from 'react';

import { Container, Content, SideInfo } from './Post.style';

import svg_copy from '../../../assets/copy.svg';
import svg_heart from '../../../assets/heart.svg';
import svg_heart_red from '../../../assets/heart_red.svg';
import PostMap from '../PostMap/PostMap';
import Axios from 'axios';
import StateContext from '../../../StateContext';

function Post({ userData, center }) {
  const [userImage, setUserImage] = useState();
  const appState = useContext(StateContext);

  useEffect(() => {
    Axios.get(`user/photos/${userData.userId}`, { headers: { authorization: appState.user.jwtkey } })
      .then((response) => {
        setUserImage(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <Container key={userData._id}>
      <SideInfo>
        <div className="profile-pic">{userImage ? <img src={`data:image/jpeg;base64,${userImage}`} /> : <img src="https://avatarfiles.alphacoders.com/893/thumb-89303.gif" alt="" />}</div>
        <div className="actions">
          {/* <div>
            <img src={svg_heart} alt="" />
            <p>Amei</p>
          </div> */}
          {userData.userId == appState.user.id ? (
            <></>
          ) : (
            <div>
              <img src={svg_copy} alt="" />
              <p>Copiar</p>
            </div>
          )}
        </div>
      </SideInfo>
      <Content>
        <div className="name">
          <p>{userData.title}</p>
          <p className="date">criado em {userData.date}</p>
        </div>
        <p className="comment">{userData.description}</p>
        <div className="photo">
          <PostMap data={userData} center={center} />
        </div>
      </Content>
    </Container>
  );
}

export default Post;
