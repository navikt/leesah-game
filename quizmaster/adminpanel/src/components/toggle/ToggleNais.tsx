import { useToggle } from '../../hooks/useToggle';
import './ToggleNais.scss';

export const ToggleNais = () => {
    const [toggle, setToggle] = useToggle();
    console.log(toggle);
    return (
        <div className="toggle-switch">
            <input type="checkbox" className="toggle-switch-checkbox" name="nais" id="nais" onClick={setToggle} />
            <label className="toggle-switch-label" htmlFor="nais">
                <span className="toggle-switch-inner" />
                <span className="toggle-switch-switch" />
            </label>
        </div>
    );
};
