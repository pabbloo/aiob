import React, { useState, useEffect, useContext } from 'react';
import { useMutation } from 'react-query';

import Modal from '../Modal/Modal';

import './LoginForm.css';

import { authorizeUser } from '../../RequestHelper/RequestHelper';
import { ApplicationContext } from '../../ApplicationContext/ApplicationProvider';

const LoginForm = ({handleOnClose, isModalOpen}) => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    // const {setToken, setUsername, setUserId} = useContext(ApplicationContext);
    // const loginQuery = useMutation(authorizeUser, { onSuccess: (response) => {
    //     if (response?.data) {
    //         const { data } = response;
    //         setToken(data.token);
    //         setRole(data.role);
    //         setUsername(data.username);
    //         setUserId(data.id);
    //     }
    // }});

    const handleOnLoginChange = e => setLogin(e.target.value);
    const handleOnPasswordChange = e => setPassword(e.target.value);
    const handleOnCloseModal = e => {
        e.preventDefault();
        handleOnClose();
    }

    const resetInputStates = () => {
        setLogin('');
        setPassword('');
    }

    const handleOnSubmit = e => {
        e.preventDefault();
        if (!login || !password) {
            alert('Dane logowania nie zostały podane');
            return
        }
        // loginQuery.mutate({login, password}, {onSuccess: (response) => {
        //     if (response.status === SUCCESS_CODE)
        //     {
        //         alert('Zalogowano');
        //         handleOnClose();
        //     } else {
        //         alert("Podany login lub hasło jest niepoprawne");
        //     }
        // }, onError: (error) => {
        //     alert(`Wystąpił błąd: ${error.message}, spróbuj wykonać operacje ponownie lub skontaktuj się z administratorem`);
        // }});
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
                    <button onClick={handleOnCloseModal} type="button" className="button cancel-button">Anuluj</button>
                    <button type="submit" className="button add-button">Zaloguj</button>
                </div>
            </form>
        </Modal>
    )
}

export default LoginForm;