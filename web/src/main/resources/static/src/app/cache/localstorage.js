export function set(key, value, ttmls) {
    localStorage.setItem(
        key,
        JSON.stringify({
            value: value,
            expiresAt: Date.now() + ttmls
        })
    );
}

export function get(key){
    const raw = localStorage.getItem(key);
    if(!raw) 
        return null;
    const data = JSON.parse(raw);
    if(Date.now() > data.expiresAt) {
        localStorage.removeItem(key);
        return null;
    }

    return data.value;
}

export function remove(key) {
    localStorage.removeItem(key);
}

export function clear() {  
    localStorage.clear();
}