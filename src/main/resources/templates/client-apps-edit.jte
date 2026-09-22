@import es.maneldevs.domain.model.Client
@import es.maneldevs.domain.model.ClientApp

@param String userEmail
@param Client client
@param ClientApp clientApp
@param String error

@template.layout.main(title = "Edit client app", userEmail = userEmail, activeItem = "clients", content = @`
    <form method="post" action="/web/clients/${client.getId()}/apps/${clientApp.getApp().getId()}/edit" class="col-12 col-lg-6">
        @if(!error.isEmpty())
        <div class="alert alert-danger">${error}</div>
        @endif

        <div class="mb-3">
            <label class="form-label">Client</label>
            <input type="text" class="form-control" value="${client.getName()}" disabled>
        </div>

        <div class="mb-3">
            <label class="form-label">App</label>
            <input type="text" class="form-control" value="${clientApp.getApp().getName()}" disabled>
        </div>

        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" id="name" name="name" class="form-control" value="${clientApp.getName()}" required autofocus>
        </div>

        <div class="mb-3 form-check">
            <input type="checkbox" id="active" name="active" class="form-check-input" checked="${clientApp.getActive()}">
            <label for="active" class="form-check-label">Active</label>
        </div>

        <div class="mb-3">
            <label for="apiKey" class="form-label">API Key</label>
            <div class="input-group">
                <input type="text" id="apiKey" name="apiKey" class="form-control" readonly placeholder="Leave blank to keep the current key">
                <button type="button" id="generateApiKeyBtn" class="btn btn-outline-secondary"
                    data-generate-url="/web/clients/${client.getId()}/apps/generate-api-key">Generate</button>
            </div>
            <div class="form-text">Generating a new key replaces the current one. Copy it now, it will not be shown again.</div>
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">Save</button>
            <a href="/web/clients/${client.getId()}/apps" class="btn btn-outline-secondary">Cancel</a>
        </div>
    </form>
    <script src="/static/js/client-apps.js"></script>
`)
