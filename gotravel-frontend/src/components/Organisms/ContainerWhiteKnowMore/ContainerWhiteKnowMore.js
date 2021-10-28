import React from 'react';
import { ContainerInfo, Container } from './ContainerWhiteKnowMore.style';
import InfoKnowMore from '../../Molecules/InfoKnowMore';
import { ReactComponent as Image } from '../../../assets/garoto-viajante.svg';

const ContainerWhiteKnowMore = ({ titleColorInfo, textTitleInfo, textColorInfo, textInfo, textTitleInfo2, textInfo2 }) => (
  <ContainerInfo>
    <Container>
      <InfoKnowMore titleColor={titleColorInfo} textTitle={textTitleInfo} textColor={textColorInfo} textInfo={textInfo} textTitle2={textTitleInfo2} textInfo2={textInfo2} />
      <Image />
    </Container>
  </ContainerInfo>
);

export default ContainerWhiteKnowMore;
