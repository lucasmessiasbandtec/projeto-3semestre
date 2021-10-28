import React, { useContext } from 'react';
import Layout from '../../components/Layout';
import ContainerTop from '../../components/Organisms/ContainerTop';
import ContainerKnowMore from '../../components/Organisms/ContainerKnowMore';
import ContainerWhiteKnowMore from '../../components/Organisms/ContainerWhiteKnowMore';
import DispatchContext from '../../DispatchContext';
import Footer from '../../components/Organisms/Footer/Footer';

const Institucional = () => {
  const appDispatch = useContext(DispatchContext);
  appDispatch({ type: 'is-institutional' });

  return (
    <Layout>
      <ContainerTop textButton="Entrar" textColorButton="#2D73DD" />
      <ContainerKnowMore titleColorInfo="#000000" textTitleInfo="Diga onde você quer ir" textColorInfo="#FFFFFF" textInfo="Informe sua localização e o seu destino" textTitleInfo2="Filtre com base nos seus serviços favoritos" textInfo2="Selecione o que você quer encontrar pelo caminho" />
      <ContainerWhiteKnowMore titleColorInfo="#2D73DD" textTitleInfo="Adicione os estabelecimentos" textColorInfo="#000000" textInfo="Adicione-os ao seu itinerário pessoal" textTitleInfo2="Dê um GO! e explore" textInfo2="Consulte o seu planejamento quando quiser durante a viagem" />
      <Footer />
    </Layout>
  );
};

export default Institucional;
