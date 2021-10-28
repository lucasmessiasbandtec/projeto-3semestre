import React from 'react';
import { InfoWrapper, InfoTitleWrapper, InfoTextWrapper, SecondInfoWrapper } from './InfoKnowMore.style';

const InfoKnowMore = ({
    titleColor,
    textTitle,
    textColor,
    textInfo,
    textTitle2,
    textInfo2
}) => (
    <InfoWrapper>
        <InfoTitleWrapper color={titleColor}>{textTitle}</InfoTitleWrapper>
        <InfoTextWrapper color={textColor}>{textInfo}</InfoTextWrapper>

        <SecondInfoWrapper>
            <InfoTitleWrapper color={titleColor}>{textTitle2}</InfoTitleWrapper>
            <InfoTextWrapper color={textColor}>{textInfo2}</InfoTextWrapper>
        </SecondInfoWrapper>
    </InfoWrapper>
)

export default InfoKnowMore;