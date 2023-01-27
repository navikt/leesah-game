import './ToggleNais.scss';
import React from 'react';
import { BodyShort } from '@navikt/ds-react';
// @ts-ignore
import NaisLogo from '../../ikoner/naislogo.svg';

interface ToggleNaisProps {
    setToggle: () => void;
}

export const ToggleNais = ({ setToggle }: ToggleNaisProps) => {
    return (
        <>
            <img src={NaisLogo} alt="Nais Logo" className="naislogo" />
            <div className="toggle-switch">
                <div className="button r" id="button-1">
                    <input type="checkbox" className="checkbox" onClick={setToggle} />
                    <div className="knobs"></div>
                    <div className="layer"></div>
                </div>
            </div>
        </>
    );
};
