import React, { useState, useRef, useEffect } from 'react';
import { useMutation } from 'react-query';

import Modal from '../Modal/Modal';

// import { registerAccount } from '../../RequestHelper/RequestHelper';

const RegisterForm = ({handleOnClose, isModalOpen}) => {
    // const registerQuery = useMutation(registerAccount);
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repeatedPassword, setRepeatedPassword] = useState('');

    const handleOnUsernameChange = e => setUsername(e.target.value);
    const handleOnEmailChange = e => setEmail(e.target.value);
    const handleOnPasswordChange = e => setPassword(e.target.value);
    const handleOnRepeatedPasswordChange = e => setRepeatedPassword(e.target.value);

    const resetInputStates = () => {
        setUsername('');
        setEmail('');
        setPassword('');
        setRepeatedPassword('');
    }
    
    const handleOnCloseModal = e => {
        e.preventDefault();
        handleOnClose();
    }

    const handleOnSubmit = e => {
        e.preventDefault();
        // registerQuery.mutate({username, password, email, userInfo: {birthDate}}, {onSuccess: (response) => {
        //     if (response.status === SUCCESS_CODE)
        //     {
        //         alert('Konto założone');
        //         resetInputStates();
        //     } else {
        //         alert(`Serwer wysłał odpowiedź ze statusem ${response.status}, spróbuj ponownie za chwile lub skontaktuj się z administratorem`);
        //     }
        // }, onError: (error) => {
        //     alert(`Wystąpił błąd: ${error.message}, spróbuj wykonać operacje ponownie lub skontaktuj się z administratorem`);
        // }});
        resetInputStates();
    }

    useEffect(() => {
        if (isModalOpen) {
            resetInputStates();
        }
    }, [isModalOpen]);

    return (
        <Modal handleOnClose={handleOnClose} isOpen={isModalOpen} shouldBeClosedOnOutsideClick={false}>
            <div className="modal-header">
                Rejestracja
            </div>
            <form className="modal-form" method="post" onSubmit={handleOnSubmit}>
                <div className="modal-row">
                    <label className="modal-label">
                        Nazwa użytkownika
                        <br/>
                        <input type="text" value={username} onChange={handleOnUsernameChange} className="modal-input"/>
                    </label>
                </div>
                <div className="modal-row">
                    <label className="modal-label">
                        Email
                        <br/>
                        <input type="text" value={email} onChange={handleOnEmailChange} className="modal-input"/>
                    </label>
                </div>
                <div className="modal-row">
                    <label className="modal-label">
                        Hasło
                        <br/>
                        <input type="password" value={password} onChange={handleOnPasswordChange} className="modal-input"/>
                    </label>
                </div>
                <div className="modal-row">
                    <label className="modal-label">
                        Powtórz hasło
                        <br/>
                        <input type="password" value={repeatedPassword} onChange={handleOnRepeatedPasswordChange}
                            className="modal-input"/>
                    </label>
                </div>
                <div className="modal-row">
                    <button onClick={handleOnCloseModal} type="button" className="button cancel-button">Anuluj</button>
                    <button type="submit" className="button add-button">Zarejestruj</button>
                </div>
            </form>
        </Modal>
    );
};

export default RegisterForm;