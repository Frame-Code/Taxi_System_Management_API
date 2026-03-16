export function parseJWT(token) {
    const payload = token.split('.')[1];
    const decoded = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
    return JSON.parse(decoded);
}

export function getTokenInfo(token) {
    const {exp, iat} = parseJWT(token);

    const now = Math.floor(Date.now() / 1000);
    const duration = exp - iat;
    const remaining = exp - now;

    return {
        expireAt: new Date(exp * 1000),
        issuedAt: new Date(exp * 1000),
        duration: Math.round(duration / 60),
        remaining: remaining
    };
}