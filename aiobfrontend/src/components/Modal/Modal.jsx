import React, {useEffect, useRef} from 'react';
import ReactDOM from 'react-dom';

import "./Modal.css";

const Modal = ({children, handleOnClose, isOpen, shouldBeClosedOnOutsideClick, className}) => {
    const modalRef = useRef(null);
    const previousActiveElement = useRef(null);

    useEffect(() => {
        if(!modalRef.current) {
            return;
        }

        const { current: modalWindow } = modalRef;

        if (isOpen) {
            previousActiveElement.current = document.activeElement;
            modalWindow.showModal();
        } else if (previousActiveElement.current) {
            modalWindow.close();
            previousActiveElement.current.focus();
        }
    }, [isOpen]);

    useEffect(() => {
        const { current: modalWindow} = modalRef;

        const handleCancel = e => {
            e.preventDefault();
            handleOnClose();
        }

        modalWindow.addEventListener('cancel', handleCancel);

        return () => {
            modalWindow.removeEventListener('cancel', handleCancel);
        }
    }, [handleOnClose])

    const handleOutsideClick = (e) => {
        const { current } = modalRef;

        if(shouldBeClosedOnOutsideClick && e.target === current) {
            handleOnClose();
        }
    }

    return ReactDOM.createPortal((
        <dialog ref={modalRef} className={`modal-style ${className}`} onClick={handleOutsideClick}>
            {children}
        </dialog>
    ), document.body)
}

export default Modal;