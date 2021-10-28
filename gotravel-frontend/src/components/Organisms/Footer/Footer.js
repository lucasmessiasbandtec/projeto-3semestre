import React from 'react';
import { ContainerFooter, LogoWrapper, ContainerLogo, ContatoWrapper, Endereco, Telefone } from './Footer.style'
import { ReactComponent as Logo } from '../../../assets/logo-blue.svg'

const Footer = () => (
    <ContainerFooter>
        <ContainerLogo>
            <LogoWrapper>
                <Logo />
            </LogoWrapper>

            <ContatoWrapper>
                <Endereco>Endereço</Endereco>
                <Telefone>Telefone</Telefone>
            </ContatoWrapper>
        </ContainerLogo>

    </ContainerFooter>
    

)

export default Footer;
