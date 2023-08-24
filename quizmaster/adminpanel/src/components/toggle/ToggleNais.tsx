import './ToggleNais.scss';
import React from 'react';
// @ts-ignore
import NaisLogo from '../../ikoner/naislogo.svg';
import { Button } from '@navikt/ds-react';
import classNames from 'classnames';

interface ToggleNaisProps {
    setToggle: (toggle: boolean) => void;
    toggle: boolean;
}

export const ToggleNais = ({ setToggle, toggle }: ToggleNaisProps) => {
    return (
        <Button onClick={() => setToggle(!toggle)} className={classNames('toggleBtn', toggle ? 'toggleActive' : null)}>
            <img src={NaisLogo} alt="Nais Logo" className="naislogo" />
        </Button>
    );
};
