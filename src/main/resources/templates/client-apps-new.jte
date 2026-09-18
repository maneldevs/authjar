@import es.maneldevs.domain.model.App
@import es.maneldevs.domain.model.Client
@import java.util.List

@param String userEmail
@param Client client
@param List<App> apps
@param String error

@template.layout.main(title = "New client app", userEmail = userEmail, activeItem = "clients", content = @`
    <form method="post" action="/web/clients/${client.getId()}/apps" class="col-12 col-md-6 col-lg-4">
        @if(!error.isEmpty())
        <div class="alert alert-danger">${error}</div>
        @endif

        <div class="mb-3">
            <label class="form-label">Client</label>
            <input type="text" class="form-control" value="${client.getName()}" disabled>
        </div>

        <div class="mb-3">
            <label for="appId" class="form-label">App</label>
            <select id="appId" name="appId" class="form-select" required autofocus>
                @for(App app : apps)
                <option value="${app.getId()}">${app.getName()}</option>
                @endfor
            </select>
        </div>

        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" id="name" name="name" class="form-control" required>
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">Save</button>
            <a href="/web/clients/${client.getId()}/apps" class="btn btn-outline-secondary">Cancel</a>
        </div>
    </form>
`)
