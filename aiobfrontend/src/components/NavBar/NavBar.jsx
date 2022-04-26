import React, {useState, useContext} from 'react';
import {Link} from 'react-router-dom';

import './NavBar.css';

import { ApplicationContext } from '../../ApplicationContext/ApplicationProvider';
import { HR_TABLE } from '../../common/WebsitePaths';
import LoginForm from '../LoginForm/LoginForm';
import RegisterForm from '../RegisterForm/RegisterForm';

const NavBar = () => {
    const {token, setToken, setUsername, setUserId} = useContext(ApplicationContext);
    const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
    const [isRegisterModalOpen, setIsRegisterModalOpen] = useState(false);

    const handleOnLoginClose = () => setIsLoginModalOpen(false);
    const handleOnRegisterClose = () => setIsRegisterModalOpen(false);

    const handleOnClick = (e) => {
        if (e.target.innerText === "LOGOWANIE") {
            setIsLoginModalOpen(true);
        } else {
            setIsRegisterModalOpen(true);
        }
    }

    const handleLogout = (e) => {
        setToken('');
        setUsername('');
        setUserId('');
    }

    const userAccountNavBar = token ?
    (
        <div className="right-align">
            <button className="header-button" onClick={handleLogout}>WYLOGUJ</button>
        </div>
    ) : 
    (
        <div className="right-align">
            <button className="header-button" onClick={handleOnClick}>LOGOWANIE</button>
            <button className="header-button" onClick={handleOnClick}>REJESTRACJA</button>
        </div>
    )

    return (
        <header className="header">
            <Link to="/" className="header-link">STRONA GŁÓWNA</Link>
            <Link to={HR_TABLE} className="header-link">DANE PRACOWNIKÓW</Link>
            {userAccountNavBar}
            <LoginForm handleOnClose={handleOnLoginClose} isModalOpen={isLoginModalOpen}/>
            <RegisterForm handleOnClose={handleOnRegisterClose} isModalOpen={isRegisterModalOpen} />
        </header>
    )
}

export default NavBar;