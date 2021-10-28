import React, { useContext, useEffect } from 'react';
import DispatchContext from '../../../DispatchContext';

import svg_addbutton from '../../../assets/add_button.svg';
import { Container } from './style';
import { Redirect } from 'react-router-dom';

function AddPost({ text, handle }) {
  const appDispatch = useContext(DispatchContext);

  const handlePost = () => {
    appDispatch({ type: 'show-form-post' });
  };

  const handleTrips = () => {
    return <Redirect to="/" />;
  };

  return (
    <Container>
      <div onClick={handle ? handle : handlePost}>
        <img src={svg_addbutton} alt="" />
        <p>{text}</p>
      </div>
    </Container>
  );
}

export default AddPost;
