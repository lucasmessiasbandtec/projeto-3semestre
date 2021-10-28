import React, { useContext, useEffect, useState } from 'react';
import { Container, SideInfo, Content } from './FormPost.style';
import Modali, { useModali } from 'modali';

import svg_copy_greyed from '../../../assets/copy_greyed.svg';
import svg_heart_greyed from '../../../assets/heart_greyed.svg';

import DispatchContext from '../../../DispatchContext';
import Axios from 'axios';
import StateContext from '../../../StateContext';

const TripItem = ({ onClick, viagem }) => {
  return (
    <li
      key={viagem.id}
      onClick={() => {
        onClick(viagem);
      }}
      className={`trip`}
    >
      <p>{viagem.destiny}</p>
    </li>
  );
};

function FormPost() {
  const [tripMenu, setTripMenu] = useState(false);
  const [trips, setTrips] = useState(false);
  const [postText, setPostText] = useState(null);
  const [postTrip, setPostTrip] = useState(null);
  const [buttonAddTrip, setButtonAddTrip] = useState('Adicionar Viagem');

  const appDispatch = useContext(DispatchContext);
  const appState = useContext(StateContext);

  function handleClose() {
    appDispatch({ type: 'show-form-post' });
  }

  function handleTrip() {
    setTripMenu(!tripMenu);
  }

  function handleSelectTrip(viagem, handleSelected) {
    setPostTrip(viagem);
  }

  function refreshPosts() {
    appDispatch({ type: 'refresh-posts' });
  }

  function handlePublish() {
    const postBody = {
      title: appState.user.name,
      description: postText,
      likes: 0,
      trip: postTrip,
      date: null,
      userId: appState.user.id,
    };

    Axios.post(`/post/${postTrip.id}`, postBody, { headers: { authorization: appState.user.jwtkey } })
      .then((response) => {
        refreshPosts();
        handleClose();
      })
      .catch((error) => console.log(error));
  }

  useEffect(() => {
    Axios.get(`/trip/${appState.user.id}`, { headers: { authorization: appState.user.jwtkey } }).then((e) => {
      if (e.data != null) {
        const allTrips = [];

        e.data.content.forEach((viagem) => {
          allTrips.push(viagem);
        });

        setTrips(allTrips);
      } else {
        setTrips(null);
      }
    });
  }, []);

  useEffect(() => {}, [postTrip]);

  return (
    <Container>
      <div className="wrapper">
        <SideInfo>
          <div className="profile-pic">
            <img src="https://avatarfiles.alphacoders.com/893/thumb-89303.gif" alt="" />
          </div>
          <div className="actions">
            <div>
              <img src={svg_heart_greyed} alt="" />
              <p>Amei</p>
            </div>
            <div>
              <img src={svg_copy_greyed} alt="" />
              <p>Copiar</p>
            </div>
          </div>
        </SideInfo>
        <Content>
          <div className="name">
            <p>{appState.user.name}</p>
            <button onClick={handleClose}>x</button>
          </div>
          <div className="comment">
            <textarea
              onChange={(e) => {
                setPostText(e.target.value);
              }}
              placeholder="Seu comentário vem aqui"
              autoComplete="off"
              autoFocus="true"
            ></textarea>
          </div>
          <div className="buttons">
            <button className="adicionar" onClick={handleTrip}>
              {buttonAddTrip.length > 32 ? buttonAddTrip.substring(0, 32) + '...' : buttonAddTrip}
            </button>
            <button className="publicar" onClick={handlePublish}>
              Publicar
            </button>
          </div>

          {tripMenu ? (
            <ul className="yourTrips">
              {trips.length > 0 ? (
                trips.map((viagem) => {
                  return (
                    <TripItem
                      onClick={() => {
                        setButtonAddTrip(viagem.destiny);
                        handleSelectTrip(viagem);
                      }}
                      viagem={viagem}
                    />
                  );
                })
              ) : (
                <li className="trip">
                  <p>Você ainda não tem nenhuma viagem salva.</p>
                </li>
              )}
            </ul>
          ) : (
            <></>
          )}
        </Content>
      </div>
    </Container>
  );
}

export default FormPost;
