import React from 'react';
import Renderer from 'react-test-renderer';
import LoginForm from '../login-form';

const FIXTURES = {
    actions: {
        getSession: jest.fn(),
        createSession: jest.fn()
    }
};

describe('# login-form', () => {
    beforeEach(() => {
        Object.entries(FIXTURES.actions).forEach(([name, action]) => action.mockReset());
    });

    describe('## constructor', () => {
        it('### should create default object', () => {
            const component = Renderer.create(<LoginForm />);
            expect(component.toJSON()).toMatchSnapshot();
        });
    });
});
