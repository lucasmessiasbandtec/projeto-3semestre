import React, { useContext } from 'react';
import ExternNavbar from '../ExternNavbar';
import StateContext from '../../../StateContext';
import HeaderLogout from '../../Molecules/HeaderLogout/HeaderLogout';
import HeaderLogin from '../../Molecules/HeaderLogin/HeaderLogin';

function Navbar() {
  const appState = useContext(StateContext);

  return (
    // <ExternNavbar first="Home" second="Sobre">
    <ExternNavbar>{appState.logged && !appState.isInstitutionalPage ? <HeaderLogin /> : <HeaderLogout />}</ExternNavbar>
  );
}

export default Navbar;
