import React from 'react';
import { Link } from 'react-router-dom'
import { ContainerErro, Title, ImageWrapper } from './Error.style';
import { ReactComponent as Image } from '../../../assets/carro-virado.svg';
import Button from '../../Atoms/Button'

const Error = ({nenhumText, text, buttonText, textCollor}) => {
    return (
        <ContainerErro>
            <ImageWrapper>
                <Image />
            </ImageWrapper>
            <Title>
                Oops, parece que você ainda não fez {nenhumText} {text} :c
            </Title>
            <Link to='/dashboard'>
                <Button textColor={textCollor} text={buttonText} />
            </Link>
        </ContainerErro>

)};

export default Error;