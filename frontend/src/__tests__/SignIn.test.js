import {MockedProvider} from "@apollo/client/testing";
import {render, screen} from "@testing-library/react";
import SignIn from "../_component/login/SignIn";

test('Sign in header rendering', () => {
   render(
        <MockedProvider>
            <SignIn>
            </SignIn>
        </MockedProvider>
    )
    expect(screen.getByTestId('sign-in-up')).toHaveTextContent('Sign in')
})

test('Welcome message rendering', () => {
    render(
        <MockedProvider>
            <SignIn>
            </SignIn>
        </MockedProvider>
    )
    expect(screen.getByTestId('welcome-message')).toHaveTextContent('Weclome to the Gymify App')
})

test('Lock icon rendering', () => {
    render(
        <MockedProvider>
            <SignIn>
            </SignIn>
        </MockedProvider>
    )
    expect(screen.getByTestId('lock-icon')).toBeInTheDocument()
})
