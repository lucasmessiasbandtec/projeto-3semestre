import React, { useContext, useState } from 'react';
import { ViagensWrapper, BotoesWrapper } from './ContainerViagens.style';
import TitlePage from '../../Atoms/TitlePage/TitlePage';
import MapTrips from '../MapTrips';
import AddPost from '../../Molecules/AddPost';
import Axios from 'axios';
import StateContext from '../../../StateContext';

const ContainerViagens = () => {
  const [upload, setUpload] = useState(false);
  const [file, setFile] = useState();
  const appState = useContext(StateContext);

  function handleImport() {
    setUpload(!upload);
  }

  function handleExport() {
    Axios.get('/archive/txt/' + appState.user.id, { headers: { authorization: appState.user.jwtkey } })
      .then((response) => {
        const element = document.createElement('a');
        const file = new Blob([response.data], { type: 'text/plain' });
        element.href = URL.createObjectURL(file);
        element.download = 'minhas_viagens.txt';
        document.body.appendChild(element); // Required for this to work in FireFox
        element.click();
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function handleSendTxt() {
    const formData = new FormData();
    formData.append('arquivo', file);
    Axios.post('/trip/upload', formData, { headers: { authorization: appState.user.jwtkey, 'content-type': 'multipart/form-data' } })
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <ViagensWrapper>
      {upload && (
        <div className="upload-wrapper">
          <div className="upload-div">
            <h2>Exportar uma nova viagem</h2>
            <div>
              <input
                name="inputfile"
                type="file"
                accept="text/plain"
                onChange={(e) => {
                  setFile(e.target.files[0]);
                }}
              />
            </div>
            <div className="buttons">
              <button className="enviar" onClick={handleSendTxt}>
                Enviar
              </button>
              <button
                className="cancelar"
                onClick={() => {
                  setUpload(!upload);
                }}
              >
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}

      <BotoesWrapper>
        <AddPost text="Importar viagem" handle={handleImport} />
        <AddPost text="Exportar viagens" handle={handleExport} />
      </BotoesWrapper>
      <TitlePage text="SUAS VIAGENS" />
      <MapTrips />
    </ViagensWrapper>
  );
};

export default ContainerViagens;
