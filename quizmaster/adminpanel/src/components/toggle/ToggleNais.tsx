import './ToggleNais.scss';
import React from 'react';
// @ts-ignore
import NaisLogo from '../../ikoner/naislogo.svg';

interface ToggleNaisProps {
    setToggle: () => void;
    toggle: boolean;
}

export const ToggleNais = ({ setToggle, toggle }: ToggleNaisProps) => {
    return (
        <>
            <img src={NaisLogo} alt="Nais Logo" className="naislogo" />
            <div className="toggle-switch">
                <div className="button r" id="button-1">
                    <input type="checkbox" className="checkbox" onClick={setToggle} value={toggle} />
                    <div className="knobs" />
                    <div className="layer" />
                </div>
            </div>
        </>
    );
};
