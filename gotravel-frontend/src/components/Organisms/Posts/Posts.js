import Axios from 'axios';
import React, { useContext, useEffect, useState } from 'react';
import Post from '../../Molecules/Post';

import { Container, ContainerGif } from './Posts.style';
import StateContext from '../../../StateContext';
import Car from '../../../assets/car-loading.gif';

const Posts = () => {
  const appState = useContext(StateContext);
  const [posts, setPosts] = useState([]);
  const [loanding, setLoanding] = useState(false);

  useEffect(() => {
    Axios.get('/post', { headers: { authorization: appState.user.jwtkey } })
      .then((e) => {
        setLoanding(true);
        setPosts(e.data.content);
      })
      .catch((e) => {
        console.error(e);
      });
  }, [appState.refreshPosts]);

  const renderPost = () => (
    <Container>
      {posts ? (
        posts.map((post, index) => {
          console.log('POSTS', post.trip);
          const latOBJ = parseFloat(post.trip.latDestiny);
          const lngOBJ = parseFloat(post.trip.lngDestiny);

          const centerOBJ = {
            lat: latOBJ,
            lng: lngOBJ,
          };

          return <Post key={index} userData={post} center={centerOBJ}></Post>;
        })
      ) : (
        <></>
      )}
    </Container>
  );

  return loanding ? (
    renderPost()
  ) : (
    <ContainerGif id="teste">
      <img src={Car} alt="loading..." />
    </ContainerGif>
  );
};

export default Posts;
