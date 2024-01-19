export const convertUsers = (value: any) => {
    const defaultValue = '';
    if (value == null) {
        return defaultValue;
    }
    if (value === 'null') {
        return defaultValue;
    }
    if (value.indexOf("//") > -1) {
        return defaultValue;
    }
    try {
        const users = value.split(',');
        if (users && users.length > 0) {
            return value;
        }
        return defaultValue;
    } catch (e) {
        return defaultValue;
    }
}


export const convertNumber = (value: any) => {
    const defaultValue = 0;
    if (value == null) {
        return defaultValue;
    }
    if (value === 'null') {
        return defaultValue;
    }
    try {
        const number = Number(value);
        if (isNaN(number)) {
            return defaultValue;
        }
        return number;
    } catch (e) {
        return defaultValue;
    }
}