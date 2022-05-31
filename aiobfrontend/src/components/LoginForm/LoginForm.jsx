import React, { useState, useEffect, useContext } from 'react';
import { useMutation } from 'react-query';

import Modal from '../Modal/Modal';

import './LoginForm.css';

import { authorizeUser } from '../../RequestHelper/RequestHelper';
import { ApplicationContext } from '../../ApplicationContext/ApplicationProvider';

const LoginForm = ({handleOnClose, isModalOpen}) => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [mfaCode, setMfaCode] = useState('');
    const {setToken, setUsername} = useContext(ApplicationContext);
    const loginQuery = useMutation(authorizeUser, { onSuccess: (response) => {
        if (response?.data) {
            const { data } = response;
            setToken(data.jwt);
            setUsername(login);
        }
    }});

    const handleOnLoginChange = e => setLogin(e.target.value);
    const handleOnPasswordChange = e => setPassword(e.target.value);
    const handleOnMfaChange = e => setMfaCode(e.target.value);
    const handleOnCloseModal = e => {
        e.preventDefault();
        handleOnClose();
    }

    const resetInputStates = () => {
        setLogin('');
        setPassword('');
        setMfaCode('');
    }

    const handleOnSubmit = e => {
        e.preventDefault();
        if (!login || !password) {
            alert('Dane logowania nie zostały podane');
            return
        }
        loginQuery.mutate({username: login, password, mfaCode}, {onSuccess: (response) => {
            if (response.status === 200)
            {
                alert('Zalogowano');
                handleOnClose();
            } else {
                alert("Podany login lub hasło jest niepoprawne");
            }
        }, onError: (error) => {
            alert(`Wystąpił błąd: ${error.message}, spróbuj wykonać operacje ponownie lub skontaktuj się z administratorem`);
        }});
    }

    useEffect(() => {
        if (isModalOpen) {
            resetInputStates();
        }
    }, [isModalOpen])


    return (
        <Modal className="login-modal" handleOnClose={handleOnClose} isOpen={isModalOpen} shouldBeClosedOnOutsideClick={true}>
            <div className="modal-header">
                Logowanie
            </div>
            <form className="modal-form" method="post" onSubmit={handleOnSubmit}>
                <div className="modal-row">
                    <label className="modal-label">
                        <input type="text" value={login} placeholder="Email lub nazwa użytkownika" onChange={handleOnLoginChange} className="modal-input"/>
                    </label>
                </div>
                <div className="modal-row">
                    <label className="modal-label">
                        <input type="password" value={password} placeholder="Hasło" onChange={handleOnPasswordChange} className="modal-input"/>
                    </label>
                </div>
                <div className="modal-row">
                    <label className="modal-label">
                        <input type="text" value={mfaCode} placeholder="Kod MFE" onChange={handleOnMfaChange} className="modal-input"/>
                    </label>
                </div>
                <div className="modal-row">
                    <button onClick={handleOnCloseModal} type="button" className="button cancel-button">Anuluj</button>
                    <button type="submit" className="button add-button">Zaloguj</button>
                </div>
            </form>
        </Modal>
    )
}

export default LoginForm;