import React from 'react';

function useLocalStorageState(
  key: string,
  defaultValue: any,
  { serialize = JSON.stringify, deserialize = JSON.parse } = {}
) {
  const [state, setState] = React.useState(() => {
    const localStorageValue = window.localStorage.getItem(key);

    if (localStorageValue) {
      try {
        return deserialize(localStorageValue);
      } catch (error) {
        window.localStorage.removeItem(key);
      }
    }

    return typeof defaultValue === 'function' ? defaultValue() : defaultValue;
  });

  React.useEffect(() => {
    window.localStorage.setItem(key, serialize(state));
  }, [key, state, serialize]);

  return [state, setState];
}

export { useLocalStorageState };
