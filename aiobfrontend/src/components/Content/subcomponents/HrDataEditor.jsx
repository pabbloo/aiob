import React, { useState, useContext } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useParams, useNavigate} from 'react-router-dom';
import { HR_TABLE } from '../../../common/WebsitePaths';
import { editHrData, getHrEntry } from '../../../RequestHelper/RequestHelper';
import { ApplicationContext } from '../../../ApplicationContext/ApplicationProvider';

import './HrDataEditor.css';

const HrDataEditor = () => {
    const { id: hrDataId } = useParams();
    const [ name, setName ] = useState('');
    const [ surname, setSurname ] = useState('');
    const [ personalNumber, setPersonalNumber ] = useState('');
    const [ salary, setSalary ] = useState(0);
    const [ jobPosition, setJobPosition ] = useState('');
    const [ division, setDivision ] = useState('');
    const [ ifFired, setIfFired ] = useState(false);
    const {token} = useContext(ApplicationContext);
    const navigate = useNavigate();

    const editQuery = useMutation(editHrData);
    useQuery('getHrEntry', () => getHrEntry({id: hrDataId, token}), { onSuccess: (response) => {
        if (response.status === 200) {
            const { data } = response;
            setName(data.name);
            setSurname(data.surname);
            setPersonalNumber(data.personalNumber);
            setSalary(data.salary);
            setJobPosition(data.jobPosition);
            setDivision(data.division);
            setIfFired(data.firedDate ? true : false);
        }
    }})

    const handleOnNameChanged = (e) => setName(e.target.value);
    const handleOnSurnameChanged = (e) => setSurname(e.target.value);
    const handleOnPersonalNumberChanged = (e) => setPersonalNumber(e.target.value);
    const handleOnSalaryChanged = (e) => setSalary(e.target.value);
    const handleOnJobPositionChanged = (e) => setJobPosition(e.target.value);
    const handleOnDivisionChanged = (e) => setDivision(e.target.value);
    const handleOnFiredStateChanged = (e) => setIfFired(prevState => !prevState);

    const resetInputs = () => {
        setName('');
        setSurname('');
        setPersonalNumber('');
        setSalary(0);
        setJobPosition('');
        setDivision('');
        setIfFired(false);
    }

    const handleOnSubmit = (e) => {
        e.preventDefault();
        editQuery.mutate({id:hrDataId, name, surname, personalNumber, salary, jobPosition, division, token}, {onSuccess: (response) => {
            if (response.status === 200)
            {
                alert('Akcja zakonczona sukcesem');
                resetInputs();
            } else {
                alert(`Serwer wysłał odpowiedź ze statusem ${response.status}, spróbuj ponownie za chwile lub skontaktuj się z administratorem`);
            }
        }, onError: (error) => {
            alert(`Wystąpił błąd: ${error.message}, spróbuj wykonać operacje ponownie lub skontaktuj się z administratorem`);
        }});
    };

    const handleOnCancel = () => {
        navigate(HR_TABLE);
    }



    return (
        <div className="hr-data-container">
            <h1>Edytuj dane pracownika</h1>
            <form onSubmit={handleOnSubmit} className="display-grid hr-data-form">

                <div className="first-row f-column">
                    <label htmlFor="name" className="m-right-10">Imie pracownika</label>
                </div>
                <div className="first-row s-column">
                    <input id="name" className="hr-data-input" type="text" value={name} 
                        onChange={handleOnNameChanged} />
                </div>

                <div className="second-row f-column">
                    <label htmlFor="surname" className="m-right-10">Nazwisko pracownika</label>
                </div>
                <div className="second-row s-column">
                    <input id="surname" className="hr-data-input" type="text" value={surname} 
                        onChange={handleOnSurnameChanged} />
                </div>

                <div className="third-row f-column">
                    <label htmlFor="personalNumber" className="m-right-10">Identyfikator pracownika</label>
                </div>
                <div className="third-row s-column">
                    <input id="personalNumber" className="hr-data-input" type="text" value={personalNumber} 
                        onChange={handleOnPersonalNumberChanged} />
                </div>

                <div className="fourth-row f-column">
                    <label htmlFor="salary" className="m-right-10">Pensja pracownika</label>
                </div>
                <div className="fourth-row s-column">
                    <input id="salary" className="hr-data-input" type="number" value={salary} 
                        onChange={handleOnSalaryChanged} />
                </div>

                <div className="fifth-row f-column">
                    <label htmlFor="jobPosition" className="m-right-10">Stanowisko</label>
                </div>
                <div className="fifth-row s-column">
                    <input id="jobPosition" className="hr-data-input" type="text" value={jobPosition} 
                        onChange={handleOnJobPositionChanged} />
                </div>

                <div className="sixth-row f-column">
                    <label htmlFor="division" className="m-right-10">Dział</label>
                </div>
                <div className="sixth-row s-column">
                    <input id="division" className="hr-data-input" type="text" value={division} 
                        onChange={handleOnDivisionChanged} />
                </div>

                <div className="seventh-row f-column">
                    <label htmlFor="ifFired" className="m-right-10">Zwolniony</label>
                </div>
                <div className="seventh-row s-column">
                    <input id="ifFired" className="hr-data-input" type="checkbox" value={ifFired} 
                        onChange={handleOnFiredStateChanged} />
                </div>

                <div className="eight-row f-column-button">
                    <button type="button" className="button cancel-button hr-data-button" onClick={handleOnCancel}>Anuluj</button>
                </div>
                <div className="eight-row s-column">
                        <button type="submit" className="button add-button hr-data-button">Dodaj</button>
                </div>


            </form>
        </div>
    );
};

export default HrDataEditor;