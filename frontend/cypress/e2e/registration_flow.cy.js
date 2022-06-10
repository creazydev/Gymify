describe('The Registration page', () => {
    beforeEach(() => {
    })

    it('Creates new user', function () {
        const uuid = () => Cypress._.random(0, 1e6)

        const user = {
            email: `test2${uuid()}@mail.ru`,
            password: "sample123"
        };

        cy.visit('/login')

        cy.get('button[id=toggle_sign_up]').click()

        cy.get('input[id=email]').type(user.email)
        cy.get('input[id=password]').type(user.password)
        cy.get('button[type=submit]').click()

        cy.url().should('include', '/login')
    })
})
