import { createResource } from 'redux-rest-resource';

class SessionReducer {
    constructor() {
        this.STATE_PATH = 'app.session';

        const { actions, rootReducer } = createResource({
            name: 'session',
            pluralName: 'session',
            url: '/api/session'
        });

        this.reducer = rootReducer;
        Object.assign(this, actions);
    }
}

export default SessionReducer;
