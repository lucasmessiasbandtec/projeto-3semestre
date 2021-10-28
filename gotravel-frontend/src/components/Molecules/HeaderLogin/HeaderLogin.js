import React, { useContext, useEffect, useState } from 'react';
import StateContext from '../../../StateContext';
import DispatchContext from '../../../DispatchContext';
import LogoffWrapper from './HeaderLogin.style';

import svg_rectangle from '../../../assets/rectangle.svg';
import Axios from 'axios';

function HeaderLogin() {
  const [profilePic, setProfilePic] = useState();
  const [menu, setMenu] = useState(false);
  const [userImage, setUserImage] = useState();

  const appState = useContext(StateContext);
  const appDispatch = useContext(DispatchContext);

  function handleLogoff() {
    setMenu(!menu);
  }

  function logoff() {
    appDispatch({ type: 'logout' });
  }

  useEffect(() => {
    Axios.get(`user/photos/${appState.user.id}`, { headers: { authorization: appState.user.jwtkey } })
      .then((response) => {
        setUserImage(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <LogoffWrapper>
      <p>{`Bem vindo ${appState.user.name}`}</p>

      <div className="profile" onClick={handleLogoff}>
        {userImage ? <img className="profilePic" src={`data:image/jpeg;base64,${userImage}`} /> : <img className="profilePic" src={profilePic || 'https://iela.ufsc.br/sites/default/files/default_images/default-user.png'} alt="" />}
        <img className="rectangle" src={svg_rectangle} alt="" />
      </div>

      <div className={`options ${menu ? 'visible' : 'notvisible'}`}>
        <ul>
          <li key="0">Minha conta</li>
          <li key="1" onClick={logoff}>
            Sair
          </li>
        </ul>
      </div>
    </LogoffWrapper>
  );
}

export default HeaderLogin;
